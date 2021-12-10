package com.example.Cloudtakmicenje;

import com.example.Cloudtakmicenje.models.GetBody;
import com.example.Cloudtakmicenje.models.PayLoad;
import com.example.Cloudtakmicenje.calculator.Calculator;
import com.example.Cloudtakmicenje.repository.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CloudCalculatorController {

    @PostMapping("/actions")
    @ResponseBody
    public List<PayLoad> createProduct(@RequestBody List<PayLoad> payLoads) {
        for (PayLoad payLoad:payLoads){
            Repository.getInstance().addPayLoad(payLoad);
        }
        return Repository.getInstance().getAllPayLoads();
    }

    @GetMapping("/user/{id}/costs")
    public GetBody getUserCosts(@RequestParam(name = "untilDate") long untilDate,
                                @PathVariable int id,
                                @RequestParam(name = "serviceTypes",required = false) List<String> serviceTypes){
        //Repository.getInstance().addPayLoad(new PayLoad("FUNC",id,"EXEC",1609500600,5));
        GetBody getBody = new Calculator(untilDate,id,serviceTypes).calcCost();
        return getBody;
    }



}