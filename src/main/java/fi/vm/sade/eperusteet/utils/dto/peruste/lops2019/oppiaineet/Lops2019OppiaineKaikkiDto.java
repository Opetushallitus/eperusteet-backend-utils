package fi.vm.sade.eperusteet.utils.dto.peruste.lops2019.oppiaineet;

import fi.vm.sade.eperusteet.utils.dto.peruste.lops2019.oppiaineet.moduuli.Lops2019ModuuliDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lops2019OppiaineKaikkiDto extends Lops2019OppiaineBaseDto {
    private List<Lops2019ModuuliDto> moduulit = new ArrayList<>();
    private List<Lops2019OppiaineKaikkiDto> oppimaarat = new ArrayList<>();
}
