import { useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom";

export function HomePage() {
  const [id, setId] = useState("");
  const navigate = useNavigate();

  function abrirDetalhe(e: FormEvent) {
    e.preventDefault();
    const trim = id.trim();
    if (trim) {
      navigate(`/solicitacoes/${encodeURIComponent(trim)}`);
    }
  }

  return (
    <div className="space-y-8">
      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h1 className="text-2xl font-semibold text-emerald-900">
          Cidades mais sustentáveis
        </h1>
        <p className="mt-2 text-slate-600">
          O ObservaAção permite que cidadãos registrem problemas urbanos
          (iluminação, buracos, lixo, poda), em apoio à{" "}
          <strong>ODS 11</strong>.
        </p>
      </section>

      <section className="rounded-xl border border-dashed border-slate-300 bg-white p-6">
        <h2 className="text-lg font-medium">Abrir detalhe da solicitação</h2>
        <p className="mt-1 text-sm text-slate-500">
          Enquanto as telas de listagem (#24 / #25) não entram, use o ID do
          MongoDB ou crie uma solicitação pela API/CLI.
        </p>
        <form onSubmit={abrirDetalhe} className="mt-4 flex flex-wrap gap-2">
          <input
            type="text"
            value={id}
            onChange={(e) => setId(e.target.value)}
            placeholder="ID da solicitação"
            className="min-w-[16rem] flex-1 rounded-lg border border-slate-300 px-3 py-2 text-sm"
          />
          <button
            type="submit"
            className="rounded-lg bg-emerald-700 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-800"
          >
            Ver detalhe
          </button>
        </form>
      </section>
    </div>
  );
}
