import { TestBed } from '@angular/core/testing';
import { Livehiculos } from './livehiculos';

describe('Livehiculos', () => {
  let service: Livehiculos;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Livehiculos);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
