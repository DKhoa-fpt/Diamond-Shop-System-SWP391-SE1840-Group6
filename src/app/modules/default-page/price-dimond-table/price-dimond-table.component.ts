import {Component, OnInit,Inject,
  LOCALE_ID} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {forkJoin} from "rxjs";

import {
  formatNumber
}
  from '@angular/common';


@Component({
  selector: 'app-price-dimond-table',
  templateUrl: './price-dimond-table.component.html',
  styleUrls: ['./price-dimond-table.component.css']
})
export class PriceDimondTableComponent implements OnInit{
  listHeader = ['Giá Kim Cương Siêu Rẻ 3ly6','Giá Kim Cương 3ly9','Bảng Giá Kim Cương Tự Nhiên 4ly1','Giá Kim Cương 4ly5','Giá Kim Cương 5ly','Kim Cương Tự Nhiên 5ly2','Giá Kim Cương 5ly3','Giá Kim Cương Viên 5ly4','Giá Kim Cương Tự Nhiên 6ly','Giá Kim Cương Thiên Nhiên 6ly2','Giá Kim Cương 6ly3<1CT','Giá Kim Cương 6ly3>1CT','Giá Kim Cương Phổ Thông 6ly8','Giá Kim Cương 7ly2','Giá Kim Cương 8ly1','Giá Kim Cương 9ly <3CT'];
  requetAll : any[];

  public constructor(private http : HttpClient) {
  }
  ngOnInit(): void {
    const request: any[] = [
      this.http.get('/assets/data/3_6mm.json'),
      this.http.get('/assets/data/3_9mm.json'),
      this.http.get('/assets/data/4_1mm.json'),
      this.http.get('/assets/data/4_5mm.json'),
      this.http.get('/assets/data/5_0mm.json'),
      this.http.get('/assets/data/5_2mm.json'),
      this.http.get('/assets/data/5_3mm.json'),
      this.http.get('/assets/data/5_4mm.json'),
      this.http.get('/assets/data/6_0mm.json'),
      this.http.get('/assets/data/6_2mm.json'),
      this.http.get('/assets/data/6_3_smallermm.json'),
      this.http.get('/assets/data/6_3_biggermm.json'),
      this.http.get('/assets/data/6_8_mm.json'),
      this.http.get('/assets/data/7_2mm.json'),
      this.http.get('/assets/data/8_1mm.json'),
      this.http.get('/assets/data/9_0mm.json'),
      ];
     forkJoin(request).subscribe((data : any[]) => {
       this.requetAll = data;
       console.log(data);
     });

  }
  convertNumber(value, locale) {
    return formatNumber(value,locale);
  }

}
