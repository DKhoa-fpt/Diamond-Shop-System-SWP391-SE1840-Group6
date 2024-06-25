import {Injectable} from "@angular/core";
import {
  formatNumber
}
  from '@angular/common';
@Injectable({ providedIn: 'root' })
export class NumberService {
  localeDefault = 'en-GB';
  convertNumber(value, locale?:any) {
    if(locale) {
      return formatNumber(value,locale);
    }else{
      return formatNumber(value,this.localeDefault);
    }
  }


}
