import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeasureJewelrySizeComponent } from './measure-jewelry-size.component';

describe('MeasureJewelrySizeComponent', () => {
  let component: MeasureJewelrySizeComponent;
  let fixture: ComponentFixture<MeasureJewelrySizeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MeasureJewelrySizeComponent]
    });
    fixture = TestBed.createComponent(MeasureJewelrySizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
