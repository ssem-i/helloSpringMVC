package kr.ac.hansung.cse.controller;

import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import kr.ac.hansung.cse.model.Offer;
import kr.ac.hansung.cse.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class OfferController {
    // Controller > Service > Dao
    @Autowired
    private OfferService offerService;

    @GetMapping("/offers")
    public String showOffers(Model model){
        List<Offer> offers = offerService.getAllOffers();
        model.addAttribute("id_offers", offers);

        return "offers";
    }

    @GetMapping("/createoffer")
    public String createOffer(Model model){
        model.addAttribute("offer", new Offer());
        return "createoffer";  // 뷰로 넘겨줌
    }
    @PostMapping("docreate")
    public String doCreate (Model model, @Valid Offer offer, BindingResult result) { // 데이터 바인딩
        //System.out.println(offer);
        if(result.hasErrors()) {    // 에러 확인
            System.out.println("== Form data does not validated ==");
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }
            return "createoffer";
        }

        // controller > service > dao
        offerService.insertOffer(offer); // 에러가 없으면 데이터베이스에 저장
        return "offercreated";
    }
}
