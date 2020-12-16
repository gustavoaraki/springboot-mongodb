package com.araki.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araki.workshopmongo.domain.Post;
import com.araki.workshopmongo.domain.User;
import com.araki.workshopmongo.dto.UserDTO;
import com.araki.workshopmongo.repository.PostRepository;
import com.araki.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public Post findById(String id) {
		Optional<Post> obj = postRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}
	
	public Post insert (Post obj) {
		return postRepository.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		postRepository.deleteById(id);
	}
	
	public Post update(Post obj) {
		Post newObj = findById(obj.getId());
		updateData(newObj, obj);
		return postRepository.save(newObj);
	}
	
	private void updateData(Post newObj, Post obj) {
		newObj.setAuthor(obj.getAuthor());
		newObj.setTitle(obj.getTitle());
		newObj.setBody(obj.getBody());
	}
	
	public List<Post> findByTitle(String text){
//		return postRepository.findByTitleContainingIgnoreCase(text);
		return postRepository.searchTitle(text);
	}

	public User fromDTO (UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());	
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate){
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 100);
		
		return postRepository.fullSearch(text, minDate, maxDate);
	}
}
