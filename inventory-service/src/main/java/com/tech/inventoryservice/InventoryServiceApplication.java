package com.tech.inventoryservice;

import com.tech.inventoryservice.model.Inventory;
import com.tech.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.persistence.criteria.CriteriaBuilder;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("IPHONE_13_WHITE");
			inventory.setQuantity(10);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("IPHONE_13_RED");
			inventory1.setQuantity(10);

			Inventory inventory2 = new Inventory();
			inventory1.setSkuCode("IPHONE_13_BLUE");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}

}
