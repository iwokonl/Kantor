package pl.kantor.backend.MVC.dto;

public record PayoutRequestDto(String receiverEmail, Double total, String currency){

}
