package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.classes.Order;
import org.example.classes.Store;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataLoader {

    public static Store readStoreConfig(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return objectMapper.readValue(new File(filePath), Store.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during store.json deserialization");
            System.exit(1);
            return null;
        }
    }

    public static List<Order> readOrders(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Order>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during orders.json deserialization");
            System.exit(1);
            return null;
        }
    }
}
