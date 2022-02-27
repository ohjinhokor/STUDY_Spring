package upload_file.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import upload_file.demo.dtos.Item;
import upload_file.demo.dtos.ItemForm;
import upload_file.demo.dtos.UploadFile;
import upload_file.demo.file.FileStore;
import upload_file.demo.repository.ItemRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form){
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        Item item = new Item();
        item.setItemName(form.getItemName());
//        item.setAttachFile(attachFile);
//        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("items/{id}")
    public String items(@PathVariable Long id, Model model){
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFUllPath(filename));
    }

//    // 다운로드 로직
//    @GetMapping("/attach/{itemId}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable long itemId) throws MalformedURLException {
//        Item item = itemRepository.findById(itemId);
//
//        // 코드 중간에 item에 대한 접근권한이 있는지 확인하는 로직이 있으면 좋다.
//        // 여기서는 있다고 가정함
//
////        String storeFileName = item.getAttachFile().getStoreFileName();
////        String uploadFileName = item.getAttachFile().getUploadFileName();
//
//        UrlResource urlResource = new UrlResource("file:" + fileStore.getFUllPath(storeFileName));
//        log.info("uploadFilename={}", uploadFileName);
//
//        String contentDisposition = "attachment; filename=\"" + uploadFileName + "\"";
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(urlResource);
//    }
}