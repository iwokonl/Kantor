package pl.zeto.backend.VMC.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(nullable = false, length = 3)
    private String code; // Kod waluty, np. "USD"

    @Column(nullable = false)
    private String name; // Pełna nazwa waluty, np. "United States Dollar"


    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code) && Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

/*
INSERT INTO currencies (id, code, name) VALUES
(1, 'AED', 'dirham arabski'),
(2, 'AFN', 'afgani'),
(3, 'ALL', 'lek albański'),
(4, 'AMD', 'dram armeński'),
(5, 'ANG', 'gulden antylski'),
(6, 'AOA', 'kwanza angolska'),
(7, 'ARS', 'peso argentyńskie'),
(8, 'AUD', 'dolar australijski'),
(9, 'AWG', 'florin arubański'),
(10, 'AZN', 'manat azerski'),
(11, 'BAM', 'marka konwertowalna'),
(12, 'BBD', 'dolar Barbadosu'),
(13, 'BDT', 'taka bengalska'),
(14, 'BGN', 'lew bułgarski'),
(15, 'BHD', 'dinar bahrański'),
(16, 'BIF', 'frank burundyjski'),
(17, 'BMD', 'dolar bermudzki'),
(18, 'BND', 'dolar brunejski'),
(19, 'BOB', 'boliviano boliwijskie'),
(20, 'BRL', 'real brazylijski'),
(21, 'BSD', 'dolar bahamski'),
(22, 'BTN', 'ngultrum butański'),
(23, 'BWP', 'pula botswańska'),
(24, 'BYN', 'rubel białoruski'),
(25, 'BZD', 'dolar belizeński'),
(26, 'CAD', 'dolar kanadyjski'),
(27, 'CDF', 'frank kongijski'),
(28, 'CHF', 'frank szwajcarski'),
(29, 'CLP', 'peso chilijskie'),
(30, 'CNY', 'juan chiński'),
(31, 'COP', 'peso kolumbijskie'),
(32, 'CRC', 'colon kostarykański'),
(33, 'CVE', 'escudo kabowerdyjskie'),
(34, 'CZK', 'korona czeska'),
(35, 'DJF', 'frank dżibutyjski'),
(36, 'DKK', 'korona duńska'),
(37, 'DOP', 'peso dominikańskie'),
(38, 'DZD', 'dinar algierski'),
(39, 'EGP', 'funt egipski'),
(40, 'ERN', 'nakfa erytrejska'),
(41, 'ETB', 'birr etiopski'),
(42, 'EUR', 'euro'),
(43, 'FJD', 'dolar fidżijski'),
(44, 'FKP', 'funt falklandzki'),
(45, 'GBP', 'funt szterling'),
(46, 'GEL', 'lari gruzińskie'),
(47, 'GHS', 'cedi ghańskie'),
(48, 'GIP', 'funt gibraltarski'),
(49, 'GMD', 'dalasi gambijskie'),
(50, 'GNF', 'frank gwinejski'),
(51, 'GTQ', 'quetzal gwatemalski'),
(52, 'GYD', 'dolar gujański'),
(53, 'HKD', 'dolar hongkoński'),
(54, 'HNL', 'lempira honduraska'),
(55, 'HRK', 'kuna chorwacka'),
(56, 'HTG', 'gourde haitańskie'),
(57, 'HUF', 'forint węgierski'),
(58, 'IDR', 'rupia indonezyjska'),
(59, 'ILS', 'nowy szekel izraelski'),
(60, 'INR', 'rupia indyjska'),
(61, 'IQD', 'dinar iracki'),
(62, 'IRR', 'rial irański'),
(63, 'ISK', 'korona islandzka'),
(64, 'JMD', 'dolar jamajski'),
(65, 'JOD', 'dinar jordański'),
(66, 'JPY', 'jen japoński'),
(67, 'KES', 'szyling kenijski'),
(68, 'KGS', 'som kirgiski'),
(69, 'KHR', 'riel kambodżański'),
(70, 'KMF', 'frank komoryjski'),
(71, 'KPW', 'won północnokoreański'),
(72, 'KRW', 'won południowokoreański'),
(73, 'KWD', 'dinar kuwejcki'),
(74, 'KYD', 'dolar kajmański'),
(75, 'KZT', 'tenge kazachskie'),
(76, 'LAK', 'kip laotański'),
(77, 'LBP', 'funt libański'),
(78, 'LKR', 'rupia lankijska'),
(79, 'LRD', 'dolar liberyjski'),
(80, 'LSL', 'loti Lesoto'),
(81, 'LTD', 'dolar litewski'),
(82, 'LVL', 'łat łotewski'),
(83, 'LYD', 'dinar libijski'),
(84, 'MAD', 'dirham marokański'),
(85, 'MDL', 'lej mołdawski'),
(86, 'MGA', 'ariary malgaski'),
(87, 'MKD', 'denar macedoński'),
(88, 'MMK', 'kyat birmański'),
(89, 'MNT', 'tugrik mongolski'),
(90, 'MOP', 'pataca makaujska'),
(91, 'MRO', 'ouguiya mauretańska'),
(92, 'MUR', 'rupia maurytyjska'),
(93, 'MVR', 'rufiyaa malediwska'),
(94, 'MWK', 'kwacha malawijska'),
(95, 'MXN', 'peso meksykańskie'),
(96, 'MYR', 'ringgit malezyjski'),
(97, 'MZN', 'metical mozambicki'),
(98, 'NAD', 'dolar namibijski'),
(99, 'NGN', 'naira nigeryjska'),
(100, 'NIO', 'cordoba nikaraguańska'),
(101, 'NOK', 'korona norweska'),
(102, 'NPR', 'rupia nepalska'),
(103, 'NZD', 'dolar nowozelandzki'),
(104, 'OMR', 'rial omański'),
(105, 'PAB', 'balboa panamska'),
(106, 'PEN', 'sol peruwiański'),
(107, 'PGK', 'kina papuaska'),
(108, 'PHP', 'peso filipińskie'),
(109, 'PKR', 'rupia pakistańska'),
(110, 'PLN', 'złoty polski'),
(111, 'PYG', 'guarani paragwajskie'),
(112, 'QAR', 'rial katarski'),
(113, 'RON', 'lej rumuński'),
(114, 'RSD', 'dinar serbski'),
(115, 'RUB', 'rubel rosyjski'),
(116, 'RWF', 'frank rwandyjski'),
(117, 'SAR', 'rial saudyjski'),
(118, 'SBD', 'dolar salomoński'),
(119, 'SCR', 'rupia seszelska'),
(120, 'SDG', 'funt sudański'),
(121, 'SEK', 'korona szwedzka'),
(122, 'SGD', 'dolar singapurski'),
(123, 'SHP', 'funt św. Heleny'),
(124, 'SLL', 'leone sierraleoński'),
(125, 'SOS', 'szyling somalijski'),
(126, 'SRD', 'dolar surinamski'),
(127, 'SSP', 'funt południowosudański'),
(128, 'STD', 'dobra saotomejska'),
(129, 'SVC', 'kolon salwadorski'),
(130, 'SYP', 'funt syryjski'),
(131, 'SZL', 'lilangeni Suazi'),
(132, 'SEK', 'korona szwedzka'),
(133, 'TJS', 'somoni tadżyckie'),
(134, 'TND', 'dinar tunezyjski'),
(135, 'TOP', 'paanga tongańska'),
(136, 'TRY', 'lira turecka'),
(137, 'TTD', 'dolar trynidadzki'),
(138, 'TWD', 'nowy dolar tajwański'),
(139, 'TZS', 'szyling tanzański'),
(140, 'UAH', 'hrywna ukraińska'),
(141, 'UGX', 'szyling ugandyjski'),
(142, 'USD', 'dolar amerykański'),
(143, 'UYU', 'peso urugwajskie'),
(144, 'UZS', 'som uzbecki'),
(145, 'VEF', 'bolivar wenezuelski'),
(146, 'VND', 'dong wietnamski'),
(147, 'VUV', 'vatu vanuackie'),
(148, 'WST', 'tala samoańska'),
(149, 'XAF', 'frank CFA'),
(150, 'XCD', 'dolar wschodniokaraibski'),
(151, 'XOF', 'frank CFA'),
(152, 'XPF', 'frank CFP'),
(153, 'YER', 'rial jemenicki'),
(154, 'ZAR', 'rand południowoafrykański'),
(155, 'ZMK', 'kwacha zambijska'),
(156, 'ZWL', 'dolar Zimbabwe');
     */

}
