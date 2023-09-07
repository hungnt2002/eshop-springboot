package estore.service.upload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadServiceImpl implements UploadService {
	@Autowired
	ServletContext app;

	@Override
	public File save(MultipartFile uploadFile, String storageFolder) {
		File dir = this.getFile(storageFolder);
		try {
			File savedFile = new File(dir, uploadFile.getOriginalFilename());
			// Sao chép tệp tải lên vào vị trí đích
			Files.copy(uploadFile.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			saveImage(uploadFile);
			return savedFile;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<File> save(MultipartFile[] uploadFiles, String storageFolder) {
		List<File> list = new ArrayList<>();
		for (MultipartFile uploadFile : uploadFiles) {
			File savedFile = this.save(uploadFile, storageFolder);
			if (savedFile != null) {
				list.add(savedFile);
			}
		}
		return list;
	}

	@Override
	public void delete(String filename, String storageFolder) {
		File dir = this.getFile(storageFolder);
		new File(dir, filename).delete();
	}

	@Override
	public File getFile(String virtualPath) {
		File file = new File(app.getRealPath(virtualPath));
		// tạo thư mục mới nếu chưa tồn tại
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
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
