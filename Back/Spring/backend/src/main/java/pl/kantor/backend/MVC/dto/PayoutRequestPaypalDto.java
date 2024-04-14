package pl.kantor.backend.MVC.dto;

public record PayoutRequestPaypalDto(String receiverEmail, Double total, String currency){

}
