
package com.example.demo.controller;

import com.example.demo.entity.DonHang;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.CTDHRepo;
import com.example.demo.repository.DonHangRepo;
import com.example.demo.repository.KhachHangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller


public class DonHangController {
    @Autowired
    private DonHangRepo donHangRepo;

    @Autowired
    private CTDHRepo ctdhRepo;

    @Autowired
    private KhachHangRepo KHRepo;
    // Hiển thị danh sách đơn hàng
    @GetMapping("/don-hang/hien-thi")
    public String hienThiDanhSach(Model model) {
        List<DonHang> listDH = donHangRepo.findAll();

        model.addAttribute("listDH", listDH);
        model.addAttribute("listKhachHang",KHRepo.findAll() );
        return "view/DonHang"; // Tên template cho danh sách đơn hàng
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/don-hang/{id}")
    public String xemChiTietDonHang(@PathVariable("id") Integer maDonHang, Model model) {
        DonHang donHang = donHangRepo.findById(maDonHang)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng với mã: " + maDonHang));

        model.addAttribute("donHang", donHang);
        return "view/ChiTietDonHang"; // Tên template cho chi tiết đơn hàng
    }
    @PostMapping("/don-hang/add")
    public String themDonHang(KhachHang khachHang, DonHang donHang) {
        donHangRepo.save(donHang);
        KHRepo.save(khachHang);
        return "redirect:/don-hang/hien-thi";
    }
    @GetMapping("/don-hang/delete/{id}")
    public String xoaDonHang(@PathVariable("id") Integer id) {
        donHangRepo.deleteById(id);
        return "redirect:/don-hang/hien-thi";
    }
}
