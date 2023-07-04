package com.demo.security.controller;

import com.demo.security.config.CustomerUserDetails;
import com.demo.security.jwt.JwtTokenProvider;
import com.demo.security.persistance.model.UserModel;
import com.demo.security.payload.LoginRequest;
import com.demo.security.payload.RegisterRequest;
import com.demo.security.response.JwtResponse;
import com.demo.security.response.MessageResponse;
import com.demo.security.service.RoleService;
import com.demo.security.service.UserService;
import com.demo.security.utils.CommonFunction;
import com.demo.security.utils.Constant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    Gson gson = new Gson();
    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@RequestBody String registerRequestString){
    //validate chuỗi string đầu vào registerRequestString với file validator/createUser.schema.json
        InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream(Constant.JSON_REQ_CREATE_USER);
        CommonFunction.jsonValidate(inputStream,registerRequestString);
        //convert dữ liệu từ string thành RegisterRequest
        RegisterRequest  registerRequest = gson.fromJson(registerRequestString,RegisterRequest.class);

        if(userService.existsByUsername(registerRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse(400,Constant.ERROR_101,"Username is duplicate",null));
        }
        if(userService.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse(400,Constant.ERROR_101,"Email is duplicate",null));
        }
        UserModel userModel =userService.createUser(registerRequest);
        if(userModel != null){
            return new ResponseEntity<>(new MessageResponse(201, Constant.SUCCESS_201,"Registered successfully", userModel), HttpStatus.CREATED);

        }
    return new ResponseEntity<>(new MessageResponse(400, Constant.ERROR_104,"Create account of user error",null), HttpStatus.BAD_REQUEST);

    }
    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        try {
        //khởi tạo Authentication => đc lấy ra từ authenticationManager => set thông số username +password => tự so sánh nhờ các pthuc đã tạo
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        // Set authentication cho nó
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //lấy ra đối tượng CustomerUserDetails
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) authentication.getPrincipal();
        // Login thành công thì mới tạo ra jwt trả về cho client
        String jwt = jwtTokenProvider.generateToken(customerUserDetails);
        // Lấy  các quyền của user
        List<String> listRoles =  customerUserDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageResponse(200, Constant.SUCCESS_200,"Login successfully",
                new JwtResponse(jwt,
                customerUserDetails.getUsername(),
                customerUserDetails.getEmail(),
                customerUserDetails.getStatus(),
                listRoles)),HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Xử lý khi sai tài khoản hoặc mật khẩu
            return ResponseEntity.badRequest().body(new MessageResponse(400,Constant.ERROR_104,"Error username or password",null));
        }
    }
}
