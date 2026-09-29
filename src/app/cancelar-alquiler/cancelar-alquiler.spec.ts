import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CancelarAlquiler } from './cancelar-alquiler';

describe('CancelarAlquiler', () => {
  let component: CancelarAlquiler;
  let fixture: ComponentFixture<CancelarAlquiler>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CancelarAlquiler],
    }).compileComponents();

    fixture = TestBed.createComponent(CancelarAlquiler);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
