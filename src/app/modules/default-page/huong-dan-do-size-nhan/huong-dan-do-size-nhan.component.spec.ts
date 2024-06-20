import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HuongDanDoSizeNhanComponent } from './huong-dan-do-size-nhan.component';

describe('HuongDanDoSizeNhanComponent', () => {
  let component: HuongDanDoSizeNhanComponent;
  let fixture: ComponentFixture<HuongDanDoSizeNhanComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HuongDanDoSizeNhanComponent]
    });
    fixture = TestBed.createComponent(HuongDanDoSizeNhanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
