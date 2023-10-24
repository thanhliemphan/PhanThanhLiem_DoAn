package com.example.PhanThanhLiem_DoAn.controller.adminController;

import com.example.PhanThanhLiem_DoAn.model.Category;
import com.example.PhanThanhLiem_DoAn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "admin/categories", method = RequestMethod.GET)
    public String categories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Category");
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        return "admin/categories";
    }
    @RequestMapping(value = "add-category", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute("categoryNew") Category category, RedirectAttributes redirectAttributes){
        try {
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:admin/categories";
    }
    @RequestMapping(value = "/admin/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){
        return categoryService.findById(id);
    }
    @RequestMapping(value = "/update-category", method = {RequestMethod.GET,RequestMethod.PUT})
    public String updateCategory(Category category, RedirectAttributes redirectAttributes){
        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        }catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/admin/categories";
    }
    @RequestMapping(value = "/delete-category", method = RequestMethod.GET)
    public String deleteCaterogy(RedirectAttributes redirectAttributes, Long id){
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Disable successfully!");
        }catch (DataIntegrityViolationException e1){
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        }catch (Exception e2){
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:admin/categories";
    }
    @RequestMapping(value = "/enable-category", method = RequestMethod.GET)
    public String enableCategory(Long id,RedirectAttributes redirectAttributes){
        try {
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully!");
        }catch (DataIntegrityViolationException e1){
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        }catch (Exception e2){
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:admin/categories";
    }
}
