import {Injectable} from '@angular/core';
import {polyfillCountryFlagEmojis} from "country-flag-emoji-polyfill";

@Injectable({
  providedIn: 'root'
})
export class CurrencyFlagsService {

  constructor() {
    polyfillCountryFlagEmojis();
  }

  currencyFlags = {
    'AED': '🇦🇪', // United Arab Emirates
    'AFN': '🇦🇫', // Afghanistan
    'ALL': '🇦🇱', // Albania
    'AMD': '🇦🇲', // Armenia
    'ANG': '🇧🇶', // Netherlands Antilles (deprecated)
    'AOA': '🇦🇴', // Angola
    'ARS': '🇦🇷', // Argentina
    'AUD': '🇦🇺', // Australia
    'AWG': '🇦🇼', // Aruba
    'AZN': '🇦🇿', // Azerbaijan
    'BAM': '🇧🇦', // Bosnia and Herzegovina
    'BBD': '🇧🇧', // Barbados
    'BDT': '🇧🇩', // Bangladesh
    'BGN': '🇧🇬', // Bulgaria
    'BHD': '🇧🇭', // Bahrain
    'BIF': '🇧🇮', // Burundi
    'BMD': '🇧🇲', // Bermuda
    'BND': '🇧🇳', // Brunei Darussalam
    'BOB': '🇧🇴', // Bolivia
    'BRL': '🇧🇷', // Brazil
    'BSD': '🇧🇸', // Bahamas
    'BTN': '🇧🇹', // Bhutan
    'BWP': '🇧🇼', // Botswana
    'BYR': '🇧🇾', // Belarus
    'BZD': '🇧🇿', // Belize
    'CAD': '🇨🇦', // Canada
    'CDF': '🇨🇩', // Democratic Republic of the Congo
    'CHF': '🇨🇭', // Switzerland
    'CLP': '🇨🇱', // Chile
    'CNY': '🇨🇳', // China
    'COP': '🇨🇴', // Colombia
    'CRC': '🇨🇷', // Costa Rica
    'CVE': '🇨🇻', // Cape Verde
    'CUP': '🇨🇺', // Cuba
    'CZK': '🇨🇿', // Czech Republic
    'DJF': '🇩🇯', // Djibouti
    'DKK': '🇩🇰', // Denmark
    'DOP': '🇩🇴', // Dominican Republic
    'DZD': '🇩🇿', // Algeria
    'EGP': '🇪🇬', // Egypt
    'ERN': '🇪🇷', // Eritrea
    'ETB': '🇪🇹', // Ethiopia
    'EUR': '🇪🇺', // Eurozone (common currency for multiple countries)
    'FJD': '🇫🇯', // Fiji
    'FKP': '🇫🇰', // Falkland Islands (Malvinas)
    'GBP': '🇬🇧', // United Kingdom
    'GEL': '🇬🇪', // Georgia
    'GHS': '🇬🇭', // Ghana
    'GIP': '🇬🇮', // Gibraltar
    'GMD': '🇬🇲', // The Gambia
    'GNF': '🇬🇳', // Guinea
    'GTQ': '🇬🇹', // Guatemala
    'GYD': '🇬🇾', // Guyana
    'HKD': '🇭🇰', // Hong Kong SAR, China
    'HNL': '🇭🇳', // Honduras
    'HRK': '🇭🇷', // Croatia
    'HTG': '🇭🇹', // Haiti
    'HUF': '🇭🇺', // Hungary
    'IDR': '🇮🇩', // Indonesia
    'ILS': '🇮🇱', // Israel
    'INR': '🇮🇳', // India
    'IQD': '🇮🇶', // Iraq
    'IRR': '🇮🇷', // Iran (Islamic Republic of)
    'ISK': '🇮🇸', // Iceland
    'JMD': '🇯🇲', // Jamaica
    'JOD': '🇯🇴', // Jordan
    'JPY': '🇯🇵', // Japan
    'KES': '🇰🇪', // Kenya
    'KGS': '🇰🇬', // Kyrgyzstan
    'KHR': '🇰🇭', // Cambodia
    'KMF': '🇰🇲', // Comoros
    'KPW': '🇰🇵', // Democratic People's Republic of Korea
    'KRW': '🇰🇷', // Republic of Korea
    'KWD': '🇰🇼', // Kuwait
    'KYD': '🇰🇾', // Cayman Islands
    'KZT': '🇰🇿', // Kazakhstan
    'LAK': '🇱🇦', // Lao People's Democratic Republic
    'LBP': '🇱🇧', // Lebanon
    'LKR': '🇱🇰', // Sri Lanka
    'LRD': '🇱🇷', // Liberia
    'LSL': '🇱🇸', // Lesotho
    'LYD': '🇱🇾', // Libya
    'MAD': '🇲🇦', // Morocco
    'MDL': '🇲🇩', // Moldova (Republic of)
    'MGA': '🇲🇬', // Madagascar
    'MKD': '🇲🇰', // North Macedonia
    'MMK': '🇲🇲', // Myanmar
    'MNT': '🇲🇳', // Mongolia
    'MOP': '🇲🇴', // Macao SAR, China
    'MRU': '🇲🇷', // Mauritania
    'MUR': '🇲🇺', // Mauritius
    'MVR': '🇲🇻', // Maldives
    'MWK': '🇲🇼', // Malawi
    'MXN': '🇲🇽', // Mexico
    'MYR': '🇲🇾', // Malaysia
    'MZN': '🇲🇿', // Mozambique
    'NAD': '🇳🇦', // Namibia
    'NGN': '🇳🇬', // Nigeria
    'NIO': '🇳🇮', // Nicaragua
    'NOK': '🇳🇴', // Norway
    'NPR': '🇳🇵', // Nepal
    'NZD': '🇳🇿', // New Zealand
    'OMR': '🇴🇲', // Oman
    'PAB': '🇵🇦', // Panama
    'PEN': '🇵🇪', // Peru
    'PGK': '🇵🇬', // Papua New Guinea
    'PHP': '🇵🇭', // Philippines
    'PKR': '🇵🇰', // Pakistan
    'PLN': '🇵🇱', // Poland
    'PYG': '🇵🇾', // Paraguay
    'QAR': '🇶🇦', // Qatar
    'RON': '🇷🇴', // Romania
    'RSD': '🇷🇸', // Serbia
    'RUB': '🇷🇺', // Russian Federation
    'RWF': '🇷🇼', // Rwanda
    'SAR': '🇸🇦', // Saudi Arabia
    'SBD': '🇸🇧', // Solomon Islands
    'SCR': '🇸🇨', // Seychelles
    'SDG': '🇸🇩', // Sudan
    'SEK': '🇸🇪', // Sweden
    'SGD': '🇸🇬', // Singapore
    'SHP': '🇸🇭', // Saint Helena, Ascension and Tristan da Cunha
    'SLL': '🇸🇱', // Sierra Leone
    'SOS': '🇸🇴', // Somalia
    'SRD': '🇸🇷', // Suriname
    'SSP': '🇸🇸', // South Sudan
    'STN': '🇸🇹', // Sao Tome and Principe
    'SVC': '🇸🇻', // El Salvador
    'SYP': '🇸🇾', // Syrian Arab Republic
    'SZL': '🇸🇿', // Eswatini
    'THB': '🇹🇭', // Thailand
    'TJS': '🇹🇯', // Tajikistan
    'TMT': '🇹🇲', // Turkmenistan
    'TND': '🇹🇳', // Tunisia
    'TOP': '🇹🇴', // Tonga
    'TRY': '🇹🇷', // Turkey
    'TTD': '🇹🇹', // Trinidad and Tobago
    'TWD': '🇹🇼', // Taiwan (Province of China)
    'TZS': '🇹🇿', // Tanzania, United Republic of
    'UAH': '🇺🇦', // Ukraine
    'UGX': '🇺🇬', // Uganda
    'USD': '🇺🇸', // United States of America
    'UYU': '🇺🇾', // Uruguay
    'UZS': '🇺🇿', // Uzbekistan
    'VEF': '🇻🇪', // Venezuela
    'VND': '🇻🇳', // Viet Nam
    'VUV': '🇻🇺', // Vanuatu
    'WST': '🇼🇸', // Samoa
    'XAF': '🇨🇫', // Central African Republic
    'XCD': '🇦🇬', // Antigua and Barbuda
    'XOF': '🇧🇯', // Benin
    'XPF': '🇵🇫', // French Polynesia
    'YER': '🇾🇪', // Yemen
    'ZAR': '🇿🇦', // South Africa
    'ZMW': '🇿🇲',  // Zambia
    'ZWL': '🇿🇼'  // Zimbabwe
  };

  getCurrencyFlags() {
    return this.currencyFlags;
  }
}
