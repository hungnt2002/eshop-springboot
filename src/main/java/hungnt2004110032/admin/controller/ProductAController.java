package hungnt2004110032.admin.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hungnt2004110032.model.Category;
import hungnt2004110032.model.Product;
import hungnt2004110032.repository.service.CategoryService;
import hungnt2004110032.repository.service.ProductService;
import hungnt2004110032.service.session.SessionService;
import hungnt2004110032.service.upload.UploadService;

@Controller
public class ProductAController {
	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	UploadService uploadService;

	@Autowired
	SessionService sessionService;

	@RequestMapping("/admin/product/paginate/{pageNumber}")
	public String paginate(Model model, @PathVariable("pageNumber") Integer pageNumber) {
		sessionService.set("pageNumber", pageNumber);
		model.addAttribute("edit", false);
		return "forward:/admin/product/index";
	}

	@RequestMapping("/admin/product/category/{cid}")
	public String category(Model model, @PathVariable("cid") Integer cid) {
		sessionService.set("cid", cid);
		return "forward:/admin/product/paginate/0";
	}

	@RequestMapping("/admin/product/reset")
	public String reset(Model model) {
		model.addAttribute("edit", true);
		return "forward:/admin/product/index";
	}

	@RequestMapping("/admin/product/index")
	public String index(Model model) {
		model.addAttribute("item", new Product()); // tạo mới 1 thực thể bỏ vào model
		return this.forward(model); // gọi đến hàm forward, return kq do hàm này trả về
	}

	@RequestMapping("/admin/product/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Product item = productService.getById(id);
		model.addAttribute("item", item);
		model.addAttribute("edit", true);
		return this.forward(model);
	}

	@RequestMapping("/admin/product/create")
	public String create(Model model,
			@RequestParam("image_file") MultipartFile imageFile,
			@ModelAttribute("item") Product item) {
		if (!imageFile.isEmpty()) {
			File image = uploadService.save(imageFile, "/images/items/");
			item.setImage(image.getName());
			// saveImage(imageFile);
		}
		productService.create(item);
		model.addAttribute("message", "Tạo mới thành công!");
		model.addAttribute("edit", true);
		return this.forward(model);
	}

	@RequestMapping("/admin/product/update")
	public String update(Model model,
			@RequestParam("image_file") MultipartFile imageFile,
			@ModelAttribute("item") Product item) {
		if (!imageFile.isEmpty()) {
			File image = uploadService.save(imageFile, "/images/items/");
			item.setImage(image.getName());
			// saveImage(imageFile);
		}
		productService.update(item);
		model.addAttribute("message", "Cập nhật thành công!");
		model.addAttribute("edit", true);
		return this.forward(model);
	}

	@RequestMapping("/admin/product/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {
		productService.deleteById(id);
		model.addAttribute("message", "Xóa thành công!");

		model.addAttribute("item", new Product());
		model.addAttribute("edit", false);
		return this.forward(model);
	}

	String forward(Model model) {
		Integer cid = sessionService.get("cid", 1000);
		Integer pageNumber = sessionService.get("pageNumber", 0);
		Pageable pageable = PageRequest.of(pageNumber, 5);// mỗi trang gồm 5sp
		Page<Product> page = productService.findByCategoryId(cid, pageable);// tìm theo mã loại lấy từ session
		model.addAttribute("page", page);
		return "admin/product/index";
	}

	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryService.findAll();
	}

	public void saveImage(MultipartFile imageFile) {
		try {
			File destFile = new File("D:\\project\\DoAn-SpringbootShop-main\\src\\main\\resources\\static\\images\\items\\" + imageFile.getOriginalFilename());
			imageFile.transferTo(destFile);	
			// Ảnh đã được lưu thành công
		} catch (Exception e) {
			e.printStackTrace();
			// Xử lý lỗi nếu có
		}
	}
}