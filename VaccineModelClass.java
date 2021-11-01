package com.example.publicohelpline.vaccinemodel;

import androidx.annotation.NonNull;

public class VaccineModelClass {

   private String vaccinationCenter,vaccinationCharges,vaccinationAge,vaccinationTimings,vaccinationName,vaccineCenterTime,vaccinationCenterAddress,vaccineAvailable;

   public VaccineModelClass() {
   }

   public VaccineModelClass(String vaccinationCenter, String vaccinationCharges, String vaccinationAge, String vaccinationTimings, String vaccinationName, String vaccineCenterTime, String vaccinationCenterAddress, String vaccineAvailable) {
      this.vaccinationCenter = vaccinationCenter;
      this.vaccinationCharges = vaccinationCharges;
      this.vaccinationAge = vaccinationAge;
      this.vaccinationTimings = vaccinationTimings;
      this.vaccinationName = vaccinationName;
      this.vaccineCenterTime = vaccineCenterTime;
      this.vaccinationCenterAddress = vaccinationCenterAddress;
      this.vaccineAvailable = vaccineAvailable;
   }

   public String getVaccinationCenter() {
      return vaccinationCenter;
   }

   public void setVaccinationCenter(String vaccinationCenter) {
      this.vaccinationCenter = vaccinationCenter;
   }

   public String getVaccinationCharges() {
      return vaccinationCharges;
   }

   public void setVaccinationCharges(String vaccinationCharges) {
      this.vaccinationCharges = vaccinationCharges;
   }

   public String getVaccinationAge() {
      return vaccinationAge;
   }

   public void setVaccinationAge(String vaccinationAge) {
      this.vaccinationAge = vaccinationAge;
   }

   public String getVaccinationTimings() {
      return vaccinationTimings;
   }

   public void setVaccinationTimings(String vaccinationTimings) {
      this.vaccinationTimings = vaccinationTimings;
   }

   public String getVaccinationName() {
      return vaccinationName;
   }

   public void setVaccinationName(String vaccinationName) {
      this.vaccinationName = vaccinationName;
   }

   public String getVaccineCenterTime() {
      return vaccineCenterTime;
   }

   public void setVaccineCenterTime(String vaccineCenterTime) {
      this.vaccineCenterTime = vaccineCenterTime;
   }

   public String getVaccinationCenterAddress() {
      return vaccinationCenterAddress;
   }

   public void setVaccinationCenterAddress(String vaccinationCenterAddress) {
      this.vaccinationCenterAddress = vaccinationCenterAddress;
   }

   public String getVaccineAvailable() {
      return vaccineAvailable;
   }

   public void setVaccineAvailable(String vaccineAvailable) {
      this.vaccineAvailable = vaccineAvailable;
   }
}
