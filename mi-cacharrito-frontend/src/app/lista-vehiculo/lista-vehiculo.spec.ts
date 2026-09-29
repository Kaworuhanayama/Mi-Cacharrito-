import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListaVehiculo } from './lista-vehiculo';

describe('ListaVehiculo', () => {
  let component: ListaVehiculo;
  let fixture: ComponentFixture<ListaVehiculo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaVehiculo],
    }).compileComponents();

    fixture = TestBed.createComponent(ListaVehiculo);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
