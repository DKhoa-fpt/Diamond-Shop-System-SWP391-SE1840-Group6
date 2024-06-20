import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceDimondTableComponent } from './price-dimond-table.component';

describe('PriceDimondTableComponent', () => {
  let component: PriceDimondTableComponent;
  let fixture: ComponentFixture<PriceDimondTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PriceDimondTableComponent]
    });
    fixture = TestBed.createComponent(PriceDimondTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
