package com.example.airlinesManagment;

import org.springframework.data.jpa.repository.JpaRepository;

interface AircraftRepository extends JpaRepository<Aircraft, Long> {}