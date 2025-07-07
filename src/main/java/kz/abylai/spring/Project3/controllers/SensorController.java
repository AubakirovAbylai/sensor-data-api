package kz.abylai.spring.Project3.controllers;

import jakarta.validation.Valid;
import kz.abylai.spring.Project3.dto.SensorDTO;
import kz.abylai.spring.Project3.models.Sensor;
import kz.abylai.spring.Project3.service.SensorService;
import kz.abylai.spring.Project3.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public String getSensors(Model model) {
        model.addAttribute("sensors", sensorService.findAll());
        return "/sensors/showAllSensor";
    }

    @GetMapping("/{id}")
    public String getSensor(@PathVariable Integer id, Model model){
        Sensor sensor = sensorService.findById(id);
        SensorDTO sensorDTO = convertToSensorDTO(sensor);

        model.addAttribute("sensor", sensorDTO);
        return "/sensors/showSensor";
    }

    @GetMapping("/new")
    public String newSensor(Model model) {
        model.addAttribute("sensor", new Sensor());
        return "/sensors/new";
    }

    @PostMapping()
    public String createSensor(@ModelAttribute("sensor") @Valid SensorDTO sensorDTO,
                               BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/sensors/new";
        }

        sensorService.save(convertToSensor(sensorDTO));
        return "redirect:sensors";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("sensor", sensorService.findById(id));
        return "/sensors/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("sensor") @Valid Sensor sensor,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/sensors/edit";
        }

        sensorService.update(id, sensor);
        return "redirect:/sensors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        sensorService.delete(sensorService.findById(id));
        return "redirect:/sensors";
    }



    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
    }
}
