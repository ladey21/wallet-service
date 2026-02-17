package com.example.demo.model;

import com.example.demo.dto.TransactionRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(columnList = "idempotencyKey", unique = true))
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String idempotencyKey;

    private Integer amount;
    private Long senderId;
    private Long receiverId;
    private String type;

   public Transaction(TransactionRequest request){
       this.amount = request.getAmount();
       this.type = request.getType();
       this.receiverId = request.getReceiverId();
       this.senderId = request.getSenderId();
       this.idempotencyKey = request.getIdempotencyKey();
   }
}