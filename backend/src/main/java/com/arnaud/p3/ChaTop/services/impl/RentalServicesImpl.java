package com.arnaud.p3.ChaTop.services.impl;

import com.arnaud.p3.ChaTop.dto.RentalsDto;
import com.arnaud.p3.ChaTop.entity.Rentals;
import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.mapper.RentalsMapper;
import com.arnaud.p3.ChaTop.repository.RentalsRepository;
import com.arnaud.p3.ChaTop.repository.UsersRepository;
import com.arnaud.p3.ChaTop.services.RentalServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RentalServicesImpl implements RentalServices {
  private final RentalsRepository rentalsRepository;
  private final RentalsMapper rentalsMapper;
  private final UsersRepository usersRepository;

  @Autowired
  public RentalServicesImpl(RentalsRepository rentalsRepository, RentalsMapper rentalsMapper, UsersRepository usersRepository) {
    this.rentalsRepository = rentalsRepository;
    this.rentalsMapper = rentalsMapper;
    this.usersRepository = usersRepository;
  }


  @Override
  public RentalsDto saveRental(RentalsDto rentalsDto) {
    // 1. Convertir le DTO en entité
    Rentals rental = rentalsMapper.toEntity(rentalsDto);

    // 2. Associer le propriétaire (sinon EntityNotFoundException)
    Integer ownerId = rentalsDto.getOwnerId();
    Users owner = usersRepository.findById(ownerId)
      .orElseThrow(() -> new EntityNotFoundException("Owner not found for id " + ownerId));
    rental.setOwner(owner);

    // 3. Initialiser les timestamps
    LocalDateTime now = LocalDateTime.now();
    if (rental.getId() == null) {
      rental.setCreatedAt(now);
    }
    rental.setUpdatedAt(now);

    // 4. Enregistrer en base
    Rentals saved = rentalsRepository.save(rental);

    // 5. Reconvertir l’entité persistée en DTO et renvoyer
    return rentalsMapper.toDTO(saved);
  }


  @Override
  public List<RentalsDto> getAllRentals() {
    // 1. Récupère toutes les entités
    List<Rentals> rentals = rentalsRepository.findAll();

    // 2. Mappe chaque entité en DTO
    return rentals.stream()
      .map(rentalsMapper::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public RentalsDto findRentalById(int id) {
    Rentals rentals = rentalsRepository.findById(id).orElse(null);
    return rentalsMapper.toDTO(rentals);
  }

  @Override
  public void deleteRentalById(int id) {
    rentalsRepository.deleteById(id);
  }

  public RentalsDto updateRental(RentalsDto dto) {
    Rentals rental = rentalsRepository.findById(dto.getId())
      .orElseThrow(() -> new EntityNotFoundException("Rental not found for id " + dto.getId()));

    // Mettre à jour tous les champs que vous autorisez à modifier
    rental.setName(dto.getName());
    rental.setSurface(dto.getSurface());
    rental.setPrice(dto.getPrice());
    rental.setDescription(dto.getDescription());

    // Si vous passez aussi l’owner dans le DTO :
    if (dto.getId() != null) {
      Users owner = usersRepository.findById(dto.getOwnerId())
        .orElseThrow(() -> new EntityNotFoundException("User not found for id " + dto.getOwnerId()));
      rental.setOwner(owner);
    }

    rental.setUpdatedAt(LocalDateTime.now());
    rentalsRepository.save(rental);

    return rentalsMapper.toDTO(rental);
  }
}
