import { RhModule } from './rh.module';

describe('RhModule', () => {
  let rhModule: RhModule;

  beforeEach(() => {
    rhModule = new RhModule();
  });

  it('should create an instance', () => {
    expect(rhModule).toBeTruthy();
  });
});
