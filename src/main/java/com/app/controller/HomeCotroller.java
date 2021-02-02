package com.app.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dao.PetRepository;
import com.app.dao.PetServiceRepository;
import com.app.dao.RoomRepository;
import com.app.dao.StaffRepository;
import com.app.dao.UserRepository;
import com.app.entities.Pet;
import com.app.entities.Room;
import com.app.entities.Staff;
import com.app.entities.User;
import com.app.entities.petService;
import com.app.helper.Message;
@Controller
public class HomeCotroller {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PetRepository petRepository;
	
	//@Autowired private StaffRepository staffRepository; 
	 
	@Autowired
	private PetServiceRepository petServiceRepository;
	@Autowired
	private RoomRepository roomRepository;
	
	/* home handler */
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Pet Hostel");
		return "home";
	}
	@RequestMapping("/adminLogin")
	public String adminLogin(Model model) {
		
		return "adminLogin";
	}
	@RequestMapping("/index")
	public String dashboard(Model model, HttpSession httpSession) {
		String  uname=(String) httpSession.getAttribute("uname");
		System.out.println("Valid:"+uname);
		model.addAttribute("uname",uname);
				return "admin_dashboard";
	}
	
	@RequestMapping("/authenticate")
	public String authentication(@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model, HttpSession httpSession) {
		User user=this.userRepository.getCustByCustName(username);
		if(user!=null)
		{
		if (username.equals(user.getEmail()) && user.getRole().equals("ROLE_ADMIN")) {
			
	        	System.out.println("Valid:"+username);
	        	String uname = user.getName();
	        	httpSession.setAttribute("uname", uname);
				return "redirect:/index";
			}
		
		}
		
		return "redirect:/adminLogin";
	}
	@RequestMapping("/allCustomer")
	public String getAllCustomer(Model model) {
		
		List<User> user=this.userRepository.findAll();
		model.addAttribute("user", user);
		
		return "userList";
	}
	@RequestMapping("/deleteCust/{id}")
	public String deleteCustomer(@PathVariable("id") Integer id,Model model) {
		List<Pet> pet = this.petRepository.findPetByUserId(id);
		System.out.println("Pet Info:"+pet);
		User user=this.userRepository.findById(id).get();
		
		user.getPets().remove(pet);
		this.userRepository.deleteById(id);
		
		return "redirect:/allCustomer";
	}
	@RequestMapping("/allPets")
	public String getAllPets(Model model) {
		
		List<Pet> pets=this.petRepository.findAll();
		model.addAttribute("pets", pets);
		
		/*
		 * List<Staff> staff = this.staffRepository.findAll();
		 * model.addAttribute("staff", staff);
		 */
		 
		return "admin_petList";
	}
	/*
	 * @RequestMapping("/assignStaff") public String getAllInfo(Model model) {
	 * model.getAttribute("staff"); System.out.println();
	 * List<petService>petServiceList=this.petServiceRepository.findAll();
	 * 
	 * model.addAttribute("petServiceList", petServiceList); return
	 * "admin_petServiceList"; }
	 */
	
	/* Deleting Pet */
	
	//Delete Pet handler
		@GetMapping("/delete/{pet_id}")
		public String deletePet(@PathVariable("pet_id") Integer pet_id,Model model,HttpSession httpSession) {
			
		
					Pet pet = this.petRepository.findById(pet_id).get();
					petService service=this.petServiceRepository.findServiceByPet(pet);
					//Staff staff=this.staffRepository.findStaffByPet(pet);
					//staff.setPet(null);
					//staffRepository.save(staff);
					int userId=pet.getUser().getId();					
					User user=this.userRepository.getUserByUserId(userId);
					
					System.out.println("PET------>>>"+pet);
					Room room=this.roomRepository.findRoomByPet(pet);
					System.out.println("ROOM------>>>"+room);
					room.setPet(null);
					room.setRoomStatus(true);
					this.roomRepository.save(room);
					
					user.getPets().remove(pet);
					pet.setUser(null);
					
					
					System.out.println("service from admin---------------:"+service);
					petServiceRepository.delete(service);
					/* petServiceRepository.deleteById(service.getService_id()); */
					
					
					System.out.println("deleted Id from admin---------------:"+pet.getPet_id());
					System.out.println("pet from admin---------------:"+pet);
					petRepository.delete(pet);
					
					
					
					this.userRepository.save(user);
						
			
		
				
			return "redirect:/allPets ";
		}

		/* SignOut Handler */
	
		@RequestMapping("/signout")
		public String signOut(Model model,HttpSession httpSession) {
			httpSession.invalidate();
			return "home";
		}
	
	/* about handler */
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Pet Hostel");
		return "about";
	}
	
	
	
	
	
	/*
	 * @Autowired private CustomerRepository customerRepository;
	 * 
	 * @RequestMapping("/test")
	 * 
	 * @ResponseBody public String test() { Customer customer=new Customer();
	 * customer.setCust_name("sample"); customer.setCust_email("sample@gmail.com");
	 * customer.setCust_address("sample,pune");
	 * customer.setCust_phone("4859575652"); customer.setRole("USER");
	 * customer.setPassword("sample"); customerRepository.save(customer); return
	 * "working"; }
	 */

}
