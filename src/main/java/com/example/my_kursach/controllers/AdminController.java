package com.example.my_kursach.controllers;

import com.example.my_kursach.models.Roles;
import com.example.my_kursach.models.Supplier;
import com.example.my_kursach.models.User;
import com.example.my_kursach.repos.SupplierRepo;
import com.example.my_kursach.repos.UserRepo;
import com.example.my_kursach.services.MyMailSender;
import com.example.my_kursach.services.RandomGenerator;
import com.example.my_kursach.services.SupplierService;
import com.example.my_kursach.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserRepo userRepo;
    private final UserService userService;
    private final SupplierRepo supplierRepo;
    private final MyMailSender mailSender;
    private final RandomGenerator randomGenerator;
    private final SupplierService supplierService;
    @GetMapping("/users_list")
    public String getUsers(Model model)
    {
        Iterable<User> users= userRepo.findAll();
        model.addAttribute("users",users);
        return "users_list";
    }
    @GetMapping("/userEdit/{id}")
    public String userEditForm(Model model, @PathVariable int id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.values());
        return "userEdit";
    }
    @PostMapping("/userEdit")
    public String userSaveEditForm(

            @RequestParam("userRole") String selectedRole,
            @RequestParam("login") String username,
            @RequestParam("Iduser") int userId
    ) {

        User user = userService.findById(userId);
        user.setLogin(username);
        if (user != null) {
            userService.userSavenewRole(user, selectedRole);
        }
        return "redirect:/users_list";
    }

    @GetMapping("/userBlock/{id}")
    public String blockUser(
            @PathVariable int id
    )
    {
        User user = userService.findById(id);
        user.setActive(false);
        userRepo.save(user);
        return "redirect:/users_list";
    }
    @GetMapping("/userUnblock/{id}")
    public String UnblockUser(
            @PathVariable int id)
    {
        User user = userService.findById(id);
        user.setActive(true);
        userRepo.save(user);
        return "redirect:/users_list";
    }

    @GetMapping("/suppliers_list")
    public String getSuppliers(Model model)
    {
        Iterable<Supplier> suppliers = supplierRepo.findAll();
        model.addAttribute("tittle", "Список поставщиков");
        model.addAttribute("suppliers",suppliers);
        return "suppliers_list";
    }
    @GetMapping("/supplierAdd")
    public String AddSupplier(Model model)
    {
        model.addAttribute("tittle", "Добавление поставщика");
        return "supplierAdd";
    }
    @PostMapping ("/supplierAdd")
    public String AddSuppliertoDB(Model model, @RequestParam("companyName") String companyName, @RequestParam("address") String address,@RequestParam("contactData") String contactData, @RequestParam("e-mail") String email) throws MessagingException, UnsupportedEncodingException {
        String login=randomGenerator.generateLogin();
        String password =randomGenerator.generatePassword();
        if (userRepo.existsByLogin(login)) {
            model.addAttribute("usernameError", "Пользователь с таким логином уже существует!");
            return "supplierAdd";
        }
        if (!isValidContactData(contactData)) {
            model.addAttribute("contactDataError", "Некорректный формат контактной информации! Введите фамилию, имя и номер телефона через пробел.");
            return "supplierAdd";
        }
        // Проверка существующей почты
        if (userRepo.existsByEmail(email)) {
            model.addAttribute("emailError", "Пользователь с такой почтой уже существует!");
            return "supplierAdd";
        }
        mailSender.sendEmailForRegistry(email, login, password);

        User user = new User();

        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        userService.createSupplier(user);

        Supplier supplier=new Supplier();
          supplier.setCompanyName(companyName);
          supplier.setAddress(address);
          supplier.setContactData(contactData);
        supplier.setIdUser(user.getIdUser());

       supplierRepo.save(supplier);
        return "redirect:/suppliers_list";
    }
    private boolean isValidContactData(String contactData) {
        String regex = "^[А-Яа-я]+ [А-Яа-я]+ \\+\\d{1,3}\\d{9}$";
        return contactData.matches(regex);
    }
    @GetMapping("/supplierEdit/{id}")
    public String suppEditForm(Model model, @PathVariable int id)
    {
        Supplier supplier=supplierService.findById(id);
        model.addAttribute("tittle","Редактирование поставщика");
        model.addAttribute("supplier",supplier);
        return "supplierEdit";
    }
    @PostMapping("/supplierEdit")
    public String EditSupplier(@RequestParam("companyName") String companyName,
                               @RequestParam("address") String address,
                               @RequestParam("contactData") String contactData,
                               @RequestParam("idsupplier") int idsupplier)
    {
        Supplier updatedSupplier=new Supplier();
        updatedSupplier.setContactData(contactData);
        updatedSupplier.setCompanyName(companyName);
        updatedSupplier.setAddress(address);
        supplierService.updateSupplier(idsupplier,updatedSupplier);
        return "redirect:/suppliers_list";
    }
    @PostMapping ("/supplierDelete/{id}")
    public String DeleteSupllier(  @PathVariable("id") int idsupplier)
    {
         Supplier supplier=supplierService.findById(idsupplier);
         User user=userService.findById(supplier.getIdUser());
         if(user!=null)
         {
             userRepo.delete(user);
         }
//        supplierService.deleteSupplier(idsupplier);
        return "redirect:/suppliers_list";
    }
}
