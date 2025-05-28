package com.arnaud.p3.ChaTop.services;

import com.arnaud.p3.ChaTop.dto.RentalsDto;

import java.util.Collection;

public interface RentalServices {
    RentalsDto saveRental(RentalsDto rentalsDto);
    Collection<RentalsDto> getAllRentals();
    RentalsDto findRentalById(int id);
    void deleteRentalById(int id);
    RentalsDto updateRental(RentalsDto rentalsDto);

}
