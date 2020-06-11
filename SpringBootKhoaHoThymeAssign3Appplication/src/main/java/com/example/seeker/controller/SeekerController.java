package com.example.seeker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.seeker.entity.Seeker;
import com.example.seeker.repository.SeekerRepository;

@Controller
@RequestMapping("/seekers/")
public class SeekerController {

	private final SeekerRepository seekerRepository;

	@Autowired
	public SeekerController(SeekerRepository seekerRepository) {
		this.seekerRepository = seekerRepository;
	}

	@GetMapping("signup")
	public String showSignUpForm(Seeker seeker) {
		return "add-seeker";
	}

	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("seekers", seekerRepository.findAll());
		return "index";
	}

	@PostMapping("add")
    public String addSeeker(@Valid Seeker seeker, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-seeker";
        }

        seekerRepository.save(seeker);
		return "redirect:list";
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Seeker seeker = seekerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid seeker Id:" + id));
		model.addAttribute("seeker", seeker);
		return "update-seeker";
	}

	@PostMapping("update/{id}")
	public String updateSeeker(@PathVariable("id") long id, @Valid Seeker seeker, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			seeker.setId(id);
			return "update-seeker";
		}

		seekerRepository.save(seeker);
		model.addAttribute("seekers", seekerRepository.findAll());
		return "index";
	}

	@GetMapping("delete/{id}")
	public String deleteSeeker(@PathVariable("id") long id, Model model) {
		Seeker seeker = seekerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid seeker Id:" + id));
		seekerRepository.delete(seeker);
		model.addAttribute("seekers", seekerRepository.findAll());
		return "index";
	}
}
