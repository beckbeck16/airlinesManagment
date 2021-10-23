package com.example.airlinesManagment;

import org.springframework.data.jpa.repository.JpaRepository;

interface AirlineRepository extends JpaRepository<Airline, Long> {}
