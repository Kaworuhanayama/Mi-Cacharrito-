import { TestBed } from '@angular/core/testing';
import { Alquiler } from './alquiler';

describe('Alquiler', () => {
  let service: Alquiler;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Alquiler);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
