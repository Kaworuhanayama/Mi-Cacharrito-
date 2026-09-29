import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DevolucionAlquiler } from './devolucion-alquiler';

describe('DevolucionAlquiler', () => {
  let component: DevolucionAlquiler;
  let fixture: ComponentFixture<DevolucionAlquiler>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DevolucionAlquiler],
    }).compileComponents();

    fixture = TestBed.createComponent(DevolucionAlquiler);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
