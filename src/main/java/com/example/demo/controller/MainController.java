package com.example.demo.controller;

import com.example.demo.form.VehicleForm;
import com.example.demo.model.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private static List<Vehicle> vehicles = new ArrayList<Vehicle>();

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    //Home
    @GetMapping(value = { "/", "/index" })
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    //Show All Vehicle
    @GetMapping(value = { "/vehicleList" })
    public String vehicleList(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/Vehicles";
        vehicles = restTemplate.getForObject(apiUrl, List.class);
        model.addAttribute("vehicles", vehicles);

        return "vehicleList";
    }

    //Vehicle by id
    @GetMapping(value = { "/vehicle/{id}" })
    public String vehicle(Model model, @PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/Vehicle/" + id;
        Vehicle vehicle = restTemplate.getForObject(apiUrl, Vehicle.class);
        model.addAttribute("vehicle", vehicle);

        return "viewVehicle";
    }

    //AddForm Vehicle
    @GetMapping(value = { "/addVehicle" })
    public String showAddVehiclePage(Model model) {

        VehicleForm vehicleForm = new VehicleForm();
        model.addAttribute("vehicleForm", vehicleForm);

        return "addVehicle";
    }

    //Add Vehicle
    @PostMapping(value = { "/addVehicle" })
    public String saveVehicle(Model model, @ModelAttribute("vehicleForm") VehicleForm vehicleForm) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/Vehicle";
        restTemplate.postForObject(apiUrl, vehicleForm, Vehicle.class);

            return "redirect:/vehicleList";
    }

    //UpdateForm Vehicle
    @GetMapping(value = { "/updateVehicle/{id}" })
    public String showUpdateVehiclePage(Model model, @PathVariable int id ) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/Vehicle/" + id;
        Vehicle updateVehicleForm = restTemplate.getForObject(apiUrl, Vehicle.class);
        model.addAttribute("updateVehicleForm", updateVehicleForm);

        return "updateVehicle";
    }

    //Add Vehicle
//    @RequestMapping(value = { "/updateVehicle/{id}" }, method = RequestMethod.POST)
    @PostMapping(value = { "/updateVehicle/{id}" })
    public String updateVehicle(Model model, @ModelAttribute("vehicleForm") VehicleForm vehicleForm, @PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/Vehicle/" + id;
        restTemplate.put(apiUrl, vehicleForm, Vehicle.class);

        return "redirect:/vehicleList";
    }
    //Delete Vehicle
    @DeleteMapping(value = { "/deleteVehicle/{id}" })
    public  String deleteVehicle(@PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/Vehicle/" + id;
        restTemplate.delete(apiUrl);

        return "vehicleList";
    }


}
