package com.alex.service;

import com.alex.model.Transfer;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    public List<Transfer> findTransactions() {
        // TODO : récupérer les vraies transactions
        return new ArrayList<>();
    }
}
