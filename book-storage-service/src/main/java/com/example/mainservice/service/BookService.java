package com.example.mainservice.service;


import com.example.mainservice.feign.BookTrackerServiceClient;
//import com.example.mainservice.kafka.KafkaProd;
import com.example.mainservice.models.Book;
import com.example.mainservice.models.BookDTO;
import com.example.mainservice.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {
    private final BookTrackerServiceClient bookTrackerServiceClient;

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    public BookDTO convertToDTO(Book book){
        return modelMapper.map(book, BookDTO.class);
    }
    public Book convertToEntity(BookDTO bookDTO){
        return modelMapper.map(bookDTO, Book.class);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id).get();
    }

    public Book findByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).get();
    }

    public List<Book> findFree(){
        List<Long> ids = bookTrackerServiceClient.findFree();
        for (Long id : ids) {
            System.out.println(id);
        }
        return bookRepository.findAllByIdIn(ids);
    }

    public void changeStatus(Long id, String status){
        bookTrackerServiceClient.changeStatus(id,status);
    }

    public Book save(Book book){
        Book savedBook = bookRepository.save(book);
        bookTrackerServiceClient.create(savedBook.getId());
        return savedBook;
    }

    @Transactional
    public void delete(Long id){
        bookRepository.deleteById(id);
        bookTrackerServiceClient.delete(id);
    }
}
