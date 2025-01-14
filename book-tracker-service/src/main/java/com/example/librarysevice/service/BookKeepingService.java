package com.example.librarysevice.service;

import com.example.librarysevice.model.BookKeeping;
import com.example.librarysevice.model.Status;
import com.example.librarysevice.repository.BookKeepingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class BookKeepingService {
    private final BookKeepingRepository bookKeepingRepository;
    public void save(Long id){
        BookKeeping bookKeeping = new BookKeeping();
        bookKeeping.setId(id);
        bookKeeping.setStatus(Status.FREE);
        Random random = new Random();
        int randomMinute = random.nextInt(90);
        bookKeeping.setBorrowedAt(LocalDateTime.now());
        bookKeeping.setReturnAt(LocalDateTime.now().plusMinutes(randomMinute));
        bookKeepingRepository.save(bookKeeping);
    }

    public List<Long> findAllFree(){
        List<BookKeeping> allKeeps = bookKeepingRepository.findAll();
        List<Long> freeIds = new ArrayList<>();
        for(BookKeeping bookKeeping: allKeeps){
            if(LocalDateTime.now().isAfter(bookKeeping.getReturnAt()) || bookKeeping.getStatus().equals(Status.FREE)){
                freeIds.add(bookKeeping.getId());
            }
        }
        return freeIds;
    }

    public void changeStatus(Long id, String status){
        BookKeeping bookKeeping = bookKeepingRepository.findById(id).get();
        bookKeeping.setStatus(Status.valueOf(status));
        if(status.equals("LOCK")) {
            Random random = new Random();
            int randomMinute = random.nextInt(90);
            bookKeeping.setBorrowedAt(LocalDateTime.now());
            bookKeeping.setReturnAt(LocalDateTime.now().plusMinutes(randomMinute));
        }
        bookKeepingRepository.save(bookKeeping);
    }

    @Transactional
    public void delete(Long id){
        bookKeepingRepository.deleteById(id);
    }
}
