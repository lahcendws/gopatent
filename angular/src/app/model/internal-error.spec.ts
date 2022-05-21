import { InternalError } from './internal-error';

describe('InternalError', () => {
  it('should create an instance', () => {
    expect(new InternalError()).toBeTruthy();
  });
});
