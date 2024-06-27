import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginV3Component } from './login-v3.component';

describe('LoginV3Component', () => {
  let component: LoginV3Component;
  let fixture: ComponentFixture<LoginV3Component>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginV3Component]
    });
    fixture = TestBed.createComponent(LoginV3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
