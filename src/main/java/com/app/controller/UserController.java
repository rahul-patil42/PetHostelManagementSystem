package com.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.internal.constraintvalidators.bv.NullValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.PetRepository;
import com.app.dao.PetServiceRepository;
import com.app.dao.RoomRepository;
import com.app.dao.UserRepository;
import com.app.entities.Pet;
import com.app.entities.Room;
import com.app.entities.User;
import com.app.entities.petService;
import com.app.helper.Message;
import java.time.temporal.ChronoUnit;
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private PetServiceRepository petServiceRepository;
	
	/* Method for adding common data to response */
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		
		String username=principal.getName();
		System.out.println(username);
		//get the user using username(email)
		User user=this.userRepository.getCustByCustName(username);
		System.out.println("User:"+user);
		model.addAttribute("user",user);
		
		
		

	}
	
	//dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal) {
		
		
				return "normal/user_dashboard";
	}
	
	//showing category
	@GetMapping("/boardPet")
	public String boardPetCategory(Model  model) {
		model.addAttribute("title","Board Pet Category");
	
		return "normal/BoardPetCategory";
	}
	//Open add form Handler
	@GetMapping("/boardPetForm")
	public String boardPetForm(Model  model,@RequestParam("category") String category) {
		model.addAttribute("title","Board Pet Form");
		System.out.println("category------->"+category);
		model.addAttribute("pet", new Pet());
		model.addAttribute("category",category);
		return "normal/board_form";
	}
	//Processing add pet form
	@PostMapping("/processPet")
	public String processPet(
			@ModelAttribute Pet pet,
			@RequestParam("petImage") MultipartFile file,
			@RequestParam("pickup") String pickup,
			@RequestParam("delivery") String delivery,
			@RequestParam("pet_type") String pet_type,
			   Principal principal,HttpSession session) {
		
		System.out.println("start pet ......."+pet);
		
		try {
			
			String name=principal.getName();
			User user=this.userRepository.getCustByCustName(name);
			Room room=this.roomRepository.getRoomByRoomStatus();
			
			//processing and uploading file
			if (file.isEmpty()) {
				//if the file is empty then our message	
				System.out.println("File is empty");
				pet.setImage("pet.png");
			}else {
				//file upload to folder and update the name to contact
				pet.setImage(file.getOriginalFilename());
				File saveFile=new ClassPathResource("static/image").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is uploaded");
			}
			//System.out.println("pickup date"+pickup);
			pet.setUser(user);
			/* Parsing String value date into Date type */
			pet.setPickupDate(pickup);
			pet.setDeliveryDate(delivery);
			SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
			
			Date pickupDate=sdf.parse(pickup);
			Date deliveryDate=sdf.parse(delivery);
			long noOfDays=Math.abs(deliveryDate.getTime()-pickupDate.getTime());
			
			int diff=(int)TimeUnit.DAYS.convert(noOfDays,TimeUnit.MILLISECONDS);
			int price;
			System.out.println("time diff ------------->"+diff);
			if(pet_type.equals("Dog"))
			{
				price=120;
			}else
			{
				price=100;
			}
			long TotalPrice=diff*price;
				
			session.setAttribute("TotalPrice", TotalPrice);
			
			
			user.getPets().add(pet);
			room.setPet(pet);
			
			
			room.setRoomStatus(false);
			this.roomRepository.save(room);
			this.userRepository.save(user);
			petService petservice=new petService();
			petservice.setPet(pet);
			petservice.setUser(user);
			petServiceRepository.save(petservice);
			System.out.println("Data:"+pet);
			System.out.println("Added to data base");
			
			//Message success for user
			session.setAttribute("message", new Message("Your Pet is added...! add more. Estimated Cost:"+diff*price, "success"));
			
		} catch (NullPointerException e) {
			System.out.println("Error-->"+e.getMessage());
			e.printStackTrace();
			//Error message
			session.setAttribute("message", new Message("Rooms are full!", "danger"));
			
		}catch (Exception e) {
			System.out.println("Error-->"+e.getMessage());
			e.printStackTrace();
			//Error message
			session.setAttribute("message", new Message("something went wrong try again...!", "danger"));
			
		}
	
		return "normal/user_dashboard";
	}
	
	//Show Pets handler
	//per page=5[n]
	//current page=0[page]
	@GetMapping("/show_pets/{page}")
	public String showPets(@PathVariable("page") Integer page,Model m,Principal principal) {
		m.addAttribute("title","View pets");
		
		//Getting Pet List to show in view services
		String userName=principal.getName();
		User user=this.userRepository.getCustByCustName(userName);
		
		Pageable pageable=PageRequest.of(page, 5);
		
		Page<Pet> pets =this.petRepository.findPetByUser(user.getId(),pageable);
		
		m.addAttribute("pets", pets);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", pets.getTotalPages());
		return "normal/show_pets";
	}
	
	
	//showing details of particular pet
	@RequestMapping("/{pet_id}/pet")
	public String showPetDetails(@PathVariable("pet_id") Integer pet_id,Model model,Principal principal) {
		
		
		Optional<Pet> petOptional = this.petRepository.findById(pet_id);
		Pet pet=petOptional.get();
		
		//check for user validation as per login
		String userName=principal.getName();
		User user=this.userRepository.getCustByCustName(userName);
		if(user.getId()==pet.getUser().getId()) {
			model.addAttribute("title"," Pet - "+pet.getPet_name());
			model.addAttribute("pet", pet);
		}
		
		
		
		return "normal/pet_details";
	}
	
	//Delete Pet handler
	@GetMapping("/delete/{pet_id}")
	public String deletePet(@PathVariable("pet_id") Integer pet_id,Model model,Principal principal,HttpSession httpSession) {
		Pet pet = this.petRepository.findById(pet_id).get();
		
		
		//check for user validation as per login
				String userName=principal.getName();
				User user=this.userRepository.getCustByCustName(userName);
				Room room=this.roomRepository.findRoomByPet(pet);
				
				room.setPet(null);
				room.setRoomStatus(true);
				this.roomRepository.save(room);
		
				if(user.getId()==pet.getUser().getId()) {
					
					//pet.setUser(null);
					//Getting id which is deleted
					user.getPets().remove(pet);//removing from list,remove will work after matching to object
				
					System.out.println("deleted Id:"+pet.getPet_id());
					//deleteing Pet
					this.userRepository.save(user);
					httpSession.setAttribute("message", new Message("Pet deleted successfully....!", "success"));
				}else {
					httpSession.setAttribute("message", new Message("Not possible to delete check user details", "danger"));
				}
		
	
			
		return "redirect:/user/show_pets/0 ";
	}
	
	//Updating Pet_info
	@GetMapping("/update_pet/{pet_id}")
	public String updatePet(Model model,@PathVariable("pet_id") Integer pet_id) {
		model.addAttribute("title"," Pet - "+pet_id);
		Pet pet=this.petRepository.findById(pet_id).get();
		model.addAttribute("pet", pet);
		return "normal/update_pet";
	}
	
	
	@PostMapping("/processUpdate")
	public String updatePet(
			@ModelAttribute Pet pet,
			@RequestParam("petImage") MultipartFile file, 
			Principal principal,HttpSession session) {
		
		
		try {
			
			Pet oldPet=this.petRepository.findById(pet.getPet_id()).get();
			
			//processing and uploading file
			if (!file.isEmpty()) {
				//delete old file
				File deleteFile=new ClassPathResource("static/image").getFile();
				File file1=new File(deleteFile,oldPet.getImage());
				
				
			//updating new file
				File saveFile=new ClassPathResource("static/image").getFile();
				
				Path path=Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				pet.setImage(file.getOriginalFilename());
			}else {
				//if the file is not updated then rewriting old file
				pet.setImage(oldPet.getImage());
				
			}
				User user =this.userRepository.getCustByCustName(principal.getName());
			pet.setUser(user);
			
			
			this.petRepository.save(pet);
			System.out.println("Data:"+pet.getPet_name());
			System.out.println("Updated id"+pet.getPet_id());
			
			//Message success for user
			session.setAttribute("message", new Message("Your Pet is Updated...! ", "success"));
			
		} catch (Exception e) {
			System.out.println("Error-->"+e.getMessage());
			e.printStackTrace();
			//Error message
			session.setAttribute("message", new Message("update Failed...!", "danger"));
			
		}
		return "redirect:/user/"+pet.getPet_id()+"/pet";
		
	}
	
	
	//Profile manager handler
	@GetMapping("/profile")
	public String yoprofileurProfile(Model model) {
		model.addAttribute("title"," Your Profile");
		return "normal/profile";
	}
	
	//open settings handler
	@GetMapping("/settings")
	public String openSettings() {
		return "normal/settings";
	}
	
	
	//change Password Handler
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			Principal principal,HttpSession httpSession)
		{
		System.out.println("old password :"+oldPassword);
		System.out.println("New password :"+newPassword);
		String userName=principal.getName();
		User currentUser=this.userRepository.getCustByCustName(userName);
		//checking old password with saved one
		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			//changing password
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(currentUser);
			
			//Message success for user
			httpSession.setAttribute("message", new Message("Your password change successfully...! ", "success"));
		}else {
			//error
			httpSession.setAttribute("message", new Message("Incorrect old password  please enter correct ...! ", "danger"));
			return "redirect:/user/settings";
		}
		
		return "redirect:/user/index";
	}
	
}
