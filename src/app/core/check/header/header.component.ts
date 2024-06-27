import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  @Input() enableSearch : boolean = false;
  isLoginUser:boolean = false;
  constructor(private route: ActivatedRoute,
              private router: Router,) { }

  ngOnInit(): void {
    this.isLoginUser = localStorage.getItem("user") != null;
    console.log(localStorage.getItem("user"),this.isLoginUser);
  }

  redirectLogin() {
    const returnUrl = this.route.snapshot.queryParams['/home-page'] || '/home-page';
    this.router.navigateByUrl(returnUrl);

  }
  redirectProfile() {
    const returnUrl = this.route.snapshot.queryParams['/home-page'] || '/home-page';
    this.router.navigateByUrl(returnUrl);

  }
}
