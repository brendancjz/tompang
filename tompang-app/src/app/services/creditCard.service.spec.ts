import { TestBed } from '@angular/core/testing';
import { CreditCardService } from './creditCard.service';

describe('ConversationService', () => {
  let service: CreditCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreditCardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
