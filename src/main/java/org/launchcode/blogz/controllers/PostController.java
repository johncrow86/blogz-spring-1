package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		boolean hasError = false;
		
		if (title == ""){
			hasError = true;
			model.addAttribute("error", "Title is required");
		} else if (body == ""){
			hasError = true;
			model.addAttribute("value", title);
			model.addAttribute("error", "Body is required");
		}
		
		if (hasError == true)
			return "newpost";
		
		Post p = new Post(title, body, getUserFromSession(request.getSession()));
		postDao.save(p);
		model.addAttribute("post", p);
		
		return "post"; // TODO - this redirect should go to the new post's page  		
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		Post p = postDao.findByUid(uid);
		model.addAttribute("post", p);
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		User u = userDao.findByUsername(username);
		List<Post> posts = u.getPosts();
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
