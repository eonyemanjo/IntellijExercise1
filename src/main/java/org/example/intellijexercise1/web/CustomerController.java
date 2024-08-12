package org.example.intellijexercise1.web;

import org.example.intellijexercise1.entities.Customer;
import org.example.intellijexercise1.repositories.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes({"id"})
@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerRepository customerRepository;

    @GetMapping(path = "/")
    public String initialPage(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new Customer());
        return "Main";
    }

    @PostMapping(path = "/empInsert")
    public String addCustomer(Model model, Customer customer, HttpSession session, ModelMap mm) {
        String email = (String) session.getAttribute("id");

        if (email == null) {
            customerRepository.save(customer);
        } else {
            Customer existingCustomer = customerRepository.findByEmail(email);
            if (existingCustomer != null) {
                customerRepository.delete(existingCustomer);
            }
            customerRepository.save(customer);
            mm.put("id", null);
            session.removeAttribute("id");
        }
        return "redirect:/";
    }

    @GetMapping(path = "/Main")
    public String indexPage(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("customer", new Customer());
        return "Main";
    }

    @GetMapping(path = "/editCustomer")
    public String editCustomer(Model model, String id, HttpSession session, ModelMap mm) {
        mm.put("id", id);
        Customer customer = customerRepository.findByEmail(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
        }
        return "Main";
    }

    @GetMapping(path = "/deleteCustomer")
    public String deleteCustomer(Model model, String id) {
        Customer customer = customerRepository.findByEmail(id);
        if (customer != null) {
            customerRepository.delete(customer);
        }
        return "redirect:/Main";
    }
}
