import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormularioAlquiler } from './formulario-alquiler';

describe('FormularioAlquiler', () => {
  let component: FormularioAlquiler;
  let fixture: ComponentFixture<FormularioAlquiler>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioAlquiler],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioAlquiler);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
