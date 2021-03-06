package com.massimiliano.webapp.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.massimiliano.webapp.dtos.ReservationDTO;
import com.massimiliano.webapp.entity.Reservations;
import com.massimiliano.webapp.repository.ReservationRepository;
import com.massimiliano.webapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // notazione di servizio
@Transactional(readOnly = true) // 'readOnly = true' -> notazione per tutte le query, che siano sotto
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ReservationDTO trovaReservationsPerId(int id) {

        // verificare se l'utente non sia nullo
        Reservations reservation = reservationRepository.findById(id);
        ReservationDTO reservationDto = null;

        if (reservation != null) {
            reservationDto = modelMapper.map(reservation, ReservationDTO.class);
        }
        return reservationDto;
    }

    @Override
    public Reservations trovaReservationsPerId2(int id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<ReservationDTO> trovaPrenotazioniPerIdUtente(int idUtente) {

        List<ReservationDTO> resListDTO = new ArrayList<ReservationDTO>();
        List<Reservations> list = reservationRepository.selByIdUserLike(idUtente);

        for(int i = 0; i < list.size(); i++){
            ReservationDTO provaDTO = modelMapper.map(list.get(i), ReservationDTO.class);
            resListDTO.add(provaDTO);
        }

        return resListDTO;
    }

    @Override
    public List<ReservationDTO> selezionaTutti() {

        List<ReservationDTO> resList = new ArrayList<ReservationDTO>();
        List<Reservations> list = reservationRepository.findAll();

        for (int i = 0; i < list.size(); i++) {
            ReservationDTO provaDTO = modelMapper.map(list.get(i), ReservationDTO.class);
            resList.add(provaDTO);
        }

        return resList;
    }

    @Override
    @Transactional
    public void DelReservation(ReservationDTO reservationDTO) {

        Reservations reservation = modelMapper.map(reservationDTO, Reservations.class);
        reservationRepository.delete(reservation);
    }


    @Override
    @Transactional
    public void InsRes(Reservations prenotazione) {

        reservationRepository.save(prenotazione);
    }


}
