package com.kanu.azureimgrepo.service;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanu.azureimgrepo.config.StorageConfig;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

@Service
public class StorageService {
	
	CloudStorageAccount storageAccount;
	CloudBlobClient blobClient;
	CloudBlobContainer container;
	CloudBlockBlob blob;
	
	StorageService() throws Exception{
		storageAccount = CloudStorageAccount.parse(StorageConfig.STORAGE_CONNECTION_STRING);
		blobClient = storageAccount.createCloudBlobClient();
		container = blobClient.getContainerReference("<your_container_name>");
	}
	
	public ArrayList<String> getAllItemsLinks(String folderName) throws Exception{
		ArrayList<String> links=new ArrayList<String>();
		
		for (ListBlobItem blobItem : container.listBlobs()) {
			links.add(blobItem.getUri()+"");
		}
		return links;
	}
	public void uploadImage(InputStream is,String name,int length) throws Exception{
		 blob = container.getBlockBlobReference(name);
		 blob.upload(is,length);
		 DatabaseService.insertLink(blob.getUri()+"");
	}
}
