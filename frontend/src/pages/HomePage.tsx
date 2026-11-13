import { Link } from "react-router-dom";

export function HomePage() {
  return (
    <div className="space-y-8">
      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h1 className="text-2xl font-semibold text-emerald-900">
          Problemas urbanos, cidade melhor
        </h1>
        <p className="mt-2 text-slate-600">
          O <strong>ObservaAção</strong> é um canal para cidadãos registrarem
          ocorrências como iluminação quebrada, buracos, lixo acumulado e poda
          necessária — contribuindo para a{" "}
          <strong>ODS 11 (Cidades e Comunidades Sustentáveis)</strong>.
        </p>
        <p className="mt-2 text-sm text-slate-500">
          Cidadãos abrem solicitações; a prefeitura acompanha status e histórico
          até a resolução.
        </p>
      </section>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Começar</h2>
        <ul className="mt-3 list-inside list-disc space-y-1 text-sm text-slate-600">
          <li>
            <Link to="/cidadaos" className="text-emerald-700 hover:underline">
              Cidadãos
            </Link>{" "}
            — cadastro e listagem (em evolução)
          </li>
          <li>
            <Link
              to="/solicitacoes"
              className="text-emerald-700 hover:underline"
            >
              Solicitações
            </Link>{" "}
            — ocorrências ligadas a um cidadão
          </li>
        </ul>
      </section>
    </div>
  );
}
