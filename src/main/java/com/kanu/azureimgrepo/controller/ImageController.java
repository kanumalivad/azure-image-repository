package com.kanu.azureimgrepo.controller;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kanu.azureimgrepo.service.DatabaseService;
import com.kanu.azureimgrepo.service.ImageService;

@RestController
public class ImageController {
	
	@Autowired
	ImageService imgService;
	
	
	@RequestMapping(value = "/get/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<String> getImages(@PathVariable String keyword)
	{
		try
		{
			ArrayList<String> l;
			if(keyword.equals("all"))
				l=imgService.getImages("");
			else
				l=DatabaseService.getLinks(keyword);
			JSONArray jarr=new JSONArray();
			
			for(int i=0;i<l.size();i++)
				jarr.put(l.get(i));
			
			return new ResponseEntity(jarr.toString(),HttpStatus.OK);
		}catch(Exception e)
		{
			return new ResponseEntity("Something went wrong...!!!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
	public ResponseEntity<String> uploadImages(@RequestParam("files") MultipartFile[] files) {
		try {
			for(int i=0;i<files.length;i++) {
				imgService.uploadImage(files[i].getInputStream(),files[i].getOriginalFilename(),Integer.parseInt(files[i].getSize()+""));
			}
			return new ResponseEntity("sucess",HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Something went wrong...!!!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
