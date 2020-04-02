package pl.management.map.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.management.map.service.csv.DataRepoCSV;

@Controller
public class MapController {

    private DataRepoCSV dataRepo;

    public MapController(DataRepoCSV dataRepo) {
        this.dataRepo = dataRepo;
    }

    @RequestMapping("/")
    public String getMap(Model model) {

        model.addAttribute("comments", dataRepo.getComments());
        model.addAttribute("done", dataRepo.getDone());
        model.addAttribute("invoiced", dataRepo.getInvoiced());
        model.addAttribute("off", dataRepo.getOff());
        model.addAttribute("other", dataRepo.getOther());
        model.addAttribute("getall", dataRepo.getPointList());
        return "index";
    }

}
